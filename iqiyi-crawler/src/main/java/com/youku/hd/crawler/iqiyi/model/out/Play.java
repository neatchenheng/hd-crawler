package com.youku.hd.crawler.iqiyi.model.out;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Play {
	static Logger logger = LoggerFactory.getLogger(Play.class);
	private long albumId;
	private int categoryId;
	private long tvId;
	private String tvName;
	private String link;

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public long getTvId() {
		return tvId;
	}

	public void setTvId(long tvId) {
		this.tvId = tvId;
	}

	public String getTvName() {
		return tvName;
	}

	public void setTvName(String tvName) {
		this.tvName = tvName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public static List<Play> fromElements(Elements eles) {
		List<Play> list = new ArrayList<Play>();
		for (Element ele : eles) {
			Play p = fromElement(ele);
			if (p != null) {
				list.add(p);
			}
		}
		return list;
	}

	public static Play fromElement(Element e) {
		try {
			Play p = new Play();
			Element pica = e.select("div.site-piclist_pic").first().select("a").first();
			p.setAlbumId(Long.parseLong(pica.attr("data-qidanadd-albumid")));
			p.setCategoryId(Integer.parseInt(pica.attr("data-qidanadd-channelid")));
			p.setTvId(Long.parseLong(pica.attr("data-qidanadd-tvid")));
			Element infoa = e.select("div.site-piclist_info").first().select("a").first();
			p.setTvName(infoa.text());
			p.setLink(pica.attr("href"));
			return p;
		} catch (Exception ex) {
			logger.warn("!!!! parse e({}) with exception: {}", e, ex);
			return null;
		}
	}
}
