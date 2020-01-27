package com.sxy.community.cache;

import com.sxy.community.DTO.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDto> get(){
        List<TagDto> TagDtos=new ArrayList<>();
        //开发语言
        TagDto TagDto = new TagDto();
        TagDto.setCategoryName("开发语言");
        TagDto.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        TagDtos.add(TagDto);
        //框架
        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        TagDtos.add(framework);


        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        TagDtos.add(server);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        TagDtos.add(db);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        TagDtos.add(tool);

        return TagDtos;
    }

    /**
     * 判断标签是否有效,返回有问题的标签
     * @param tags
     * @return
     */
    public static String filterinValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        //拿到所有的值
        List<String> collect = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String strings = Arrays.asList(split).stream().filter(s ->!collect.contains(s)).collect(Collectors.joining(","));
        return strings;
    }
}
