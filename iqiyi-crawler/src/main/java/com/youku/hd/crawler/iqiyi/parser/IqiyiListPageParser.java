package com.youku.hd.crawler.iqiyi.parser;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.abola.crawler.CrawlerPack;
import com.youku.hd.crawler.iqiyi.model.out.Comment;
import com.youku.hd.crawler.iqiyi.model.out.Play;

@Service
public class IqiyiListPageParser {
	Logger logger = LoggerFactory.getLogger(IqiyiListPageParser.class);
	/**
	 * 访问iqiyi视频列表页,url示例:
	 * <pre>
	 *	http://list.iqiyi.com/www/2/-------------11-1-1-iqiyi--.html
	 * </pre> 
	 * @param channelId 频道号
	 * @param page 页号
	 * @return
	 */
	public List<Play> getPlayList(long categoryId, int page) {
		String url = new StringBuilder("http://list.iqiyi.com/www/").append(categoryId).append("/-------------11-").append(page).append("-1-iqiyi--.html").toString();	
		logger.info(">>>> try to access url: {}", url);
		Document doc = CrawlerPack.start().setRemoteEncoding("utf-8").getFromHtml(url);
		Elements eles = doc.select("div.wrapper-piclist > ul > li");
		return Play.fromElements(eles);
	}
	
	/**
	 * 访问iqiyi评论接口,获取按热度排序的前30条数据,　url示例:
	 * <pre>
	 * http://api.t.iqiyi.com/qx_api/comment/get_video_comments?albumid=534941300&categoryid=2&page=1&page_size=30&sort=hot&tvid=534941300
	 * </pre>
	 * @param categoryId 频道id
	 * @param albumId 剧集id
	 * @param tvId 视频id
	 * @return
	 */
	public List<Comment> getCommentList(long categoryId, long albumId, long tvId) {
		String url = new StringBuilder("http://api.t.iqiyi.com/qx_api/comment/get_video_comments?").append("albumid=").append(albumId)
				.append("&categoryid=").append(categoryId).append("&tvid=").append(tvId).append("&page=1&page_size=30&sort=hot").toString();
		logger.info(">>>> try to access url: {}", url);
		Document doc = CrawlerPack.start().setRemoteEncoding("utf-8").getFromJson(url);
		Elements eles = doc.select("comments");
		return Comment.fromElements(eles, categoryId, albumId, tvId);
	}
}