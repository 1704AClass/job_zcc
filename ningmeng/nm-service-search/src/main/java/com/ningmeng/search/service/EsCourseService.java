package com.ningmeng.search.service;

import com.ningmeng.framework.domain.course.CoursePub;
import com.ningmeng.framework.domain.search.CourseSearchParam;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2020/2/28.
 */
@Service
public class EsCourseService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Value("${ningmeng.course.index}")
    private String es_index;
    @Value("${ningmeng.course.type}")
    private String es_type;
    @Value("${ningmeng.course.source_field}")
    private String source_field;

    public QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam){
        //分页
        if(page<=0){
            page = 1;
        }
        if(size<=0){
            size = 10;
        }

        //设置索引
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置类型
        searchRequest.types(es_type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from((page-1)*size);
        searchSourceBuilder.size(size);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //关键字
        if(StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            //匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(),"name","teachplan","description");
            //设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("80%");
            //提升另个字段的boost值
            multiMatchQueryBuilder.field("name",10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //布尔查询
        searchSourceBuilder.query(boolQueryBuilder);

        //一级分类
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        //二级分类
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        //等级
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }
        //高量显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        //设置高量字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);

        //布尔查询
        searchSourceBuilder.query(boolQueryBuilder);

        //请求搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try{
            searchResponse = restHighLevelClient.search(searchRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

        //结果集处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        //记录总数
        long totalHits = hits.getTotalHits();
        //数据列表
        List<CoursePub> list = new ArrayList<>();
        for(SearchHit hit : searchHits){
            CoursePub coursePub = new CoursePub();
            //取出source
            Map<String,Object> sourceAsMap = hit.getSourceAsMap();
            //取出名称
            String name = (String)sourceAsMap.get("name");
            //取出高量字段内容
            Map<String,HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields!=null){
                HighlightField nameField = highlightFields.get("name");
                if(nameField!=null){
                    Text[] fragments = nameField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for(Text str : fragments){
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            coursePub.setName(name);
            //图片
            String pic = (String)sourceAsMap.get("pic");
            coursePub.setPic(pic);
            //价格
            Float price = null;
            try{
                if(sourceAsMap.get("price")!=null){
                    price = Float.parseFloat((String)sourceAsMap.get("price"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            coursePub.setPrice(price);
            Float price_old = null;
            try{
                if(sourceAsMap.get("price_old")!=null){
                    price = Float.parseFloat((String)sourceAsMap.get("price_old"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            coursePub.setPrice_old(price_old);
            list.add(coursePub);
        }
        QueryResult<CoursePub> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(totalHits);
        QueryResponseResult coursePubQueryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return coursePubQueryResponseResult;
    }


    //查询课程信息
    public Map<String, CoursePub> getall(String id) {
        //设置索引库
        SearchRequest searchRequest = new SearchRequest(es_index);
        //设置类型
        searchRequest.types(es_type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件，根据课程id查询
        searchSourceBuilder.query(QueryBuilders.termQuery("id",id));
        //取消source原字段过滤，查询所有字段
        searchSourceBuilder.fetchSource(new String[]{"name","grade","charge","pic"},new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
//执行搜索
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取搜索结果
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Map<String,CoursePub> map = new HashMap<>();
        for (SearchHit hit : searchHits) {
            String courseId = hit.getId();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String courseId1 = (String) sourceAsMap.get("id");
            String name = (String) sourceAsMap.get("name");
            String grade = (String) sourceAsMap.get("grade");
            String charge = (String) sourceAsMap.get("charge");
            String pic = (String) sourceAsMap.get("pic");
            String description = (String) sourceAsMap.get("description");
            String teachplan = (String) sourceAsMap.get("teachplan");
            CoursePub coursePub = new CoursePub();
            coursePub.setId(courseId1);
            coursePub.setName(name);
            coursePub.setPic(pic);
            coursePub.setGrade(grade);
            coursePub.setTeachplan(teachplan);
            coursePub.setDescription(description);
            map.put(courseId,coursePub);
        }
        return map;
    }
}
