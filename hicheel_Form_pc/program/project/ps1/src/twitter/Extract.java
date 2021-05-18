/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	List<String> sorted = new ArrayList<String>();
    	for (Tweet var : tweets) 
    	{ 
    		sorted.add(var.getTimestamp().toString());
    	}
    	System.out.println(sorted);
    	int size=sorted.size();
    	Timespan timespan=new Timespan(Instant.parse(sorted.get(0)), Instant.parse(sorted.get(1)));
        return timespan;
    }

	/**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	Set<String> mentionedUsers = new HashSet<String>();
    	String regex="\\B@[a-zA-Z0-9_-]+\\Bn*";
		Pattern p = Pattern.compile(regex);
    	for (Tweet var : tweets) 
    	{ 
    		String post=var.toString();
    		Matcher match=p.matcher(post);
    		while(match.find())
    		{
    			String mUser=match.group().substring(1).toLowerCase();
    			mentionedUsers.add(mUser);
    		}
    	}
    	return mentionedUsers;
    }

}
