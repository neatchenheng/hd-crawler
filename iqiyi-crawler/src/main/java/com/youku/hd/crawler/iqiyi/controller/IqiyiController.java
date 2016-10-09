package com.youku.hd.crawler.iqiyi.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youku.hd.crawler.iqiyi.model.out.Comment;
import com.youku.hd.crawler.iqiyi.model.out.Play;
import com.youku.hd.crawler.iqiyi.parser.IqiyiListPageParser;

@RestController
@RequestMapping("/")
public class IqiyiController {

	@Resource
	IqiyiListPageParser parser;

	@RequestMapping(value = "status", method = { RequestMethod.GET })
	String status() {
		return "not ready";
	}

	@RequestMapping(value = "parse", method = { RequestMethod.GET })
	String parse(@RequestParam(value = "cid", required = true) Long channelId,
			@RequestParam(value = "pn", required = false, defaultValue = "1") Integer pn) {
		List<Play> playList = parser.getPlayList(channelId, pn);
		for (Play play: playList) {
			List<Comment> comments = parser.getCommentList(play.getCategoryId(), play.getAlbumId(), play.getTvId());
		}
		return "ok";
	}
}
