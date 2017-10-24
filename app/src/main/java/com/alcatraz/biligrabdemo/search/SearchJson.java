/**
  * Copyright 2017 bejson.com 
  */
package com.alcatraz.biligrabdemo.search;
import java.util.List;

/**
 * Auto-generated: 2017-10-23 13:15:18
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SearchJson {

    
    private int numPages;
    private int code;
    private Cost_time cost_time;
    private int pagesize;
    private String suggest_keyword;
    private int numResults;
    private List<Result> result;
    private String seid;
    private String msg;
    private int egg_hit;
    private String rqt_type;
    private int page;
    

    public void setNumPages(int numPages) {
         this.numPages = numPages;
     }
     public int getNumPages() {
         return numPages;
     }

    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setCost_time(Cost_time cost_time) {
         this.cost_time = cost_time;
     }
     public Cost_time getCost_time() {
         return cost_time;
     }

    public void setPagesize(int pagesize) {
         this.pagesize = pagesize;
     }
     public int getPagesize() {
         return pagesize;
     }

    public void setSuggest_keyword(String suggest_keyword) {
         this.suggest_keyword = suggest_keyword;
     }
     public String getSuggest_keyword() {
         return suggest_keyword;
     }

    public void setNumResults(int numResults) {
         this.numResults = numResults;
     }
     public int getNumResults() {
         return numResults;
     }

    public void setResult(List<Result> result) {
         this.result = result;
     }
     public List<Result> getResult() {
         return result;
     }

    public void setSeid(String seid) {
         this.seid = seid;
     }
     public String getSeid() {
         return seid;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setEgg_hit(int egg_hit) {
         this.egg_hit = egg_hit;
     }
     public int getEgg_hit() {
         return egg_hit;
     }

    public void setRqt_type(String rqt_type) {
         this.rqt_type = rqt_type;
     }
     public String getRqt_type() {
         return rqt_type;
     }

    public void setPage(int page) {
         this.page = page;
     }
     public int getPage() {
         return page;
     }

}
