package com.youku.hd.crawler.iqiyi.model.out;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爱奇艺(iqiyi)评论
 * 
 * @author chenheng
 *
 */
public class Comment {
	static Logger logger = LoggerFactory.getLogger(Comment.class);
	/**
	 * 评论id
	 */
	private long contentId;
	/**
	 * 用户id
	 */
	private long uid;
	/**
	 * 频道id
	 */
	private long categoryId;
	/**
	 * 剧集id
	 */
	private long albumId;
	/**
	 * 视频id
	 */
	private long tvId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论热度
	 */
	private int hot;
	/**
	 * 创建时间
	 */
	private long addTime;

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public long getTvId() {
		return tvId;
	}

	public void setTvId(long tvId) {
		this.tvId = tvId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public long getAddTime() {
		return addTime;
	}

	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}
	
	public static List<Comment> fromElements(Elements eles, long categoryId, long albumId, long tvId) {
		List<Comment> list = new ArrayList<Comment>();
		for (Element ele : eles) {
			Comment p = fromElement(ele, categoryId, albumId, tvId);
			if (p != null) {
				list.add(p);
			}
		}
		return list;
	}

	public static Comment fromElement(Element e, long categoryId, long albumId, long tvId) {
		try {
			Comment p = new Comment();
			p.setContentId(Long.parseLong(e.select("contentId").text()));
			p.setContent(e.select("content").text());
			p.setHot(Integer.parseInt(e.select("hot").text()));
			p.setUid(Long.parseLong(e.select("userInfo").first().select("uid").text()));
			p.setAddTime(Long.parseLong(e.select("addTime").text()));
			p.setAlbumId(albumId);
			p.setCategoryId(categoryId);
			p.setTvId(tvId);
			return p;
		} catch (Exception ex) {
			logger.warn("!!!! parse e({}) with exception: {}", e, ex);
			return null;
		}
	}
}
