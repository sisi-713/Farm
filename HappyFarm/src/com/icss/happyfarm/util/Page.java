/* 使用方法
 * init(总记录数 当前页);
 * get取得
 */

package com.icss.happyfarm.util;


/**
 * 分页类
 * @author GF4
 */

public class Page {
	private int currentRecord=5;    //一页的最大记录数
	private int currentPage;                      //当前页面
	private int totalRecord;                      //总共记录数
	private int howManyPage;                      //一共有多少页
	private int lastPageRecordCount;              //最后一页的记录数
	private int startRecord;                      //查询当前页的开始记录
	private int endRecord;                        //查询当前页的最后记录

    public Page(int currentRecord) {
        this.currentRecord = currentRecord;
    }
    
    public void initPage(int totalRecord){
        this.totalRecord = totalRecord;
    }

	public void calcPage(){
		if(totalRecord == 0){                          //没有记录
			howManyPage = 0;
			lastPageRecordCount = 0;
			return;
		}

		if(totalRecord <= currentRecord){              //小于一页
			howManyPage = 1;
			lastPageRecordCount = totalRecord;
			return;
		}

		if(totalRecord%currentRecord == 0){             //大于一页
			howManyPage = totalRecord/currentRecord;
            lastPageRecordCount =currentRecord;
		}else{
			lastPageRecordCount = totalRecord%currentRecord;
			howManyPage = (totalRecord - lastPageRecordCount)/currentRecord + 1;
		}
	}

	public void currentPageRecord(){          //当前页的记录

		if(currentPage > howManyPage){        //当前页大于最后一页

			return;
		}

		if(currentPage == howManyPage){      //当前页等于最后一页
			startRecord = (howManyPage-1)*currentRecord + 1;
			endRecord = startRecord + lastPageRecordCount - 1;
		}else{                               //当前页小于最后一页
			startRecord = (currentPage-1)*currentRecord + 1;
			endRecord = startRecord + currentRecord - 1;
		}
	}

    public void clear(){
        currentPage = 0;                      //当前页面
        totalRecord = 0;                      //总共记录数
        howManyPage = 0;                      //一共有多少页
        lastPageRecordCount = 0;              //最后一页的记录数
        startRecord = 0;                      //查询当前页的开始记录
        endRecord = 0;                        //查询当前页的最后记录
    }
     /*
            一些getter 与 setter
     */

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

    public int getHowManyPage() {
        return howManyPage;
    }

	public int getStartRecord() {
		return startRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

	public void printInfo(){
		System.out.println("一共有"+howManyPage+"页,最后一页有"+lastPageRecordCount+"条记录");
		System.out.println("开始记录是第"+startRecord+"条 最后一条记录是第"+endRecord+"条");
	}

    public void init(int totalRecord, int currentPage){
        this.totalRecord = totalRecord;
        this.currentPage = currentPage;
        initPage(totalRecord);
        calcPage();
        setCurrentPage(currentPage);
        currentPageRecord();
    }

	   public static void main(String[] args) {
       Page page = new Page(6);
        page.init(12, 2);

        page.printInfo();
    }


}