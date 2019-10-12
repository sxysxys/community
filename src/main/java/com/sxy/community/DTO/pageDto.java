package com.sxy.community.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class pageDto {
    private List<QuestionDto> questions;
    private boolean hasPrevious;
    private boolean hasFirstPage;
    private boolean hasNext;
    private boolean hasEndPage;
    private Integer currentPage;
    private List<Integer> pages=new ArrayList<>();
    private Integer allpages;

    public void setPagination(Integer allcount, Integer page, Integer size) {
        allpages=0;
        if(allcount%size==0){
            allpages=allcount/size;
        }else {
            allpages=allcount/size+1;
        }
        if(page<1){
            page=1;
        }
        if(page>allpages){
            page=allpages;
        }
        this.currentPage=page;
        pages.add(page);
        for (int i = 1; i <=3 ; i++) {
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=allpages){
                pages.add(page+i);
            }
        }
        if(page==1){
            hasPrevious=false;
        }else{
            hasPrevious=true;
        }
        if(page==allpages){
            hasNext=false;
        }else{
            hasNext=true;
        }
        if(pages.contains(1)){
            hasFirstPage=false;
        }else {
            hasFirstPage=true;
        }
        if(pages.contains(allpages)){
            hasEndPage=false;
        }else {
            hasEndPage=true;
        }
    }
}
