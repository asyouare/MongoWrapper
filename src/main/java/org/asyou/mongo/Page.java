package org.asyou.mongo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by steven on 2016/12/14.
 */
public class Page<T> implements Serializable{
    private int pageIndex;     //当前页索引
    private int pageSize;      //页数
    private int perPageCount;  //每页记录数
    private long totalCount;   //总记录数
    private String dateFrom;   //开始日期
    private String dateTo;     //结束日期
    private List<T> list;      //当前页集合

    // region getter and setter
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPerPageCount() {
        return perPageCount;
    }

    public void setPerPageCount(int perPageCount) {
        this.perPageCount = perPageCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    //endregion

    @Override
    public String toString(){
        return String.format("[Page]: totalCount:%s , perPageCount:%s , pageSize:%s , pageIndex:%s , list size:[%s]",totalCount,perPageCount,pageSize,pageIndex,list==null?0:list.size());
    }

    public Page(int pageIndex, int perPageCount, long totalCount){
        this.pageIndex = pageIndex;
        this.perPageCount = perPageCount;
        this.totalCount = totalCount;
        count();
    }

    public Page(int pageIndex, int perPageCount, long totalCount, List<T> list){
        this.pageIndex = pageIndex;
        this.perPageCount = perPageCount;
        this.totalCount = totalCount;
        this.list = list;
        count();
    }

    public List<T> getList(){
        return this.list;
    }

    private void count(){
        int mod = (int) (totalCount % perPageCount);
        if (mod == 0) {
            pageSize = (int) (totalCount / perPageCount);
        } else {
            pageSize = (int) (totalCount / perPageCount) + 1;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
//        if (pageIndex >= pageSize)
//            pageIndex = pageSize - 1;
    }

    public Page firstPage(){
        pageIndex = 0;
        count();
        return this;
    }

    public Page lastPage(){
        pageIndex = pageSize - 1;
        count();
        return this;
    }

    public Page nextPage(){
        pageIndex++;
        count();
        return this;
    }

    public Page prePage(){
        pageIndex--;
        count();
        return this;
    }

    public Page gotoPage(int pageIndex){
        this.pageIndex = pageIndex;
        count();
        return this;
    }
}
