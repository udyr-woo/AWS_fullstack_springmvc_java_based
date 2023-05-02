package cloud.bluetea.crawl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CGVParser {
	public static void main(String[] args) throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/detail-view/?midx=85541"), 2000);
		String info = doc.selectFirst(".sect-story-movie").text();
		String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
		Element ele = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
		Elements els = doc.select(".sect-base-movie .spec dt");
		for(Element e : els) {
			Elements es = e.getElementsContainingText("감독").next().select("a");
			for(Element e2 : es) {
				String directorHref = e2.attr("href");
				String director= e2.text();
				String pidx = directorHref.substring(directorHref.lastIndexOf("=")+1);
			}
			System.out.println("==================================");
			Elements es2 = e.getElementsContainingText("배우").next().select("a");
			for(Element e2 : es2) {
				String actorHref = e2.attr("href");
				String actor= e2.text();
				String pidx = actorHref.substring(actorHref.lastIndexOf("=")+1);
			}
			System.out.println("==================================");
			Elements es3 = e.getElementsContainingText("장르");
			for(Element e2 : es3) {
				String genre = e2.text();
			}
			System.out.println("==================================");
			Elements es4 = e.getElementsContainingText("기본").next();
			for(Element e2 : es4) {
				String runningTime = e2.text().split(", ")[1];
				String nation = e2.text().split(", ")[2];
			}
		}
		Elements lis = ele.select("img");
		for(Element e:lis) {
//			System.out.println(e.attr("data-src"));
//			String href = e.selectFirst("a").attr("href");
//			String date = e.selectFirst(".txt-info").text().replaceAll("개봉", "").trim();
//			String imgSrc = e.selectFirst(".thumb-image img").attr("src");
//			String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
//			String age = e.selectFirst("i.cgvIcon").text();
//			String title = e.selectFirst(".box-contents strong.title").text();
//			
//			System.out.println(e.selectFirst(".box-contents strong.title").text());
//			System.out.println(e.selectFirst("a").attr("href"));
//			doc = Jsoup.parse(new URL("http://www.cgv.co.kr" + e.selectFirst("a").attr("href")), 2000);
		}
//		Elements els = doc.select(".sect-movie-chart");
//		System.out.println(els.size());
//		
//		for(int i = 0; i < els.size() ; i++) {
//			Element e = els.get(i);
//			System.out.println(e);
//		}
	}
}
