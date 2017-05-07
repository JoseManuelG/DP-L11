
package social;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
@EnableCaching
public class TwitterUtils {

	private static Twitter	twitter;


	public TwitterUtils() {
		if (TwitterUtils.twitter == null)
			TwitterUtils.twitter = new TwitterTemplate("KrmdNTBa8xGTkXZONOUyTTxRk", "zl84XgmBfRF7TJUPYW08JxKVINefGcB2q3QO6KjY585znCgzZd", "859442838689525765-w7Tojczsd2Gtdv6WxlaSrCHijsP20Al", "snkS93JUZcUyiHqnVv4soOFUB40zp5BTF33AwO9kUUxEf");
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Twitter twitter() {
		return TwitterUtils.twitter;
	}

	@SuppressWarnings("unused")
	public void tweetNewEvent(final String data) {
		final TimelineOperations timelineOps = TwitterUtils.twitter.timelineOperations();
		final String[] parts = data.split("#");
		final TweetData twtdata = new TweetData("Nuevo evento: " + parts[0] + " creado por: " + parts[1] + " #acmechorbies #"+parts[0].replaceAll("\\s+",""));
		Resource resource = null;
		try {
			resource = new UrlResource(parts[2]);
			if (!resource.exists()){
				final Tweet tweet = timelineOps.updateStatus(twtdata);
			}else{
				final Tweet tweet = timelineOps.updateStatus(twtdata.withMedia(resource));
			}
		} catch (final Exception e) {
		}
	}
	
	@Cacheable(value="resultfllusers",key="#screenName")
	public List<TwitterProfile> getFollowers(String screenName){
		FriendOperations friendsOps = TwitterUtils.twitter.friendOperations();
		final List<TwitterProfile> results = friendsOps.getFollowers(screenName);
		return results;
	}
	
	@Cacheable(value="latesttweets",key="#hashtag")
	public List<Tweet> recentActivity(String hashtag) {
		SearchOperations searchOps = TwitterUtils.twitter.searchOperations();
		final List<Tweet> results = searchOps.search(hashtag).getTweets();
		return results;
	}
	

}
