package com.scnu.bangzhu.ganhuocommunity.config;

/**
 * Created by bangzhu on 2016/12/22.
 */
public class Contants {
    public static String PAGE_HEADER = "<!DOCTYPE html>" +
            "<html>" +
            "<head lang=\"en\">" +
            "  <meta charset=\"UTF-8\">" +
            "  <title>Blog | Amaze UI Example</title>" +
            "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
            "  <meta name=\"viewport\"" +
            "        content=\"width=device-width, initial-scale=1\">" +
            "  <meta name=\"format-detection\" content=\"telephone=no\">" +
            "  <meta name=\"renderer\" content=\"webkit\">" +
            "  <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\"/>" +
            "  <style>" +
            "    @media only screen and (min-width: 1200px) {" +
            "      .blog-g-fixed {" +
            "        max-width: 1200px;" +
            "      }" +
            "    }" +
            "    @media only screen and (min-width: 641px) {" +
            "      .blog-sidebar {" +
            "        font-size: 1.4rem;" +
            "      }" +
            "    }" +
            "    .blog-main {" +
            "      padding: 20px 0;" +
            "    }" +
            "    .blog-title {" +
            "      margin: 10px 0 20px 0;" +
            "    }" +
            "    .blog-meta {" +
            "      font-size: 14px;" +
            "      margin: 10px 0 20px 0;" +
            "      color: #222;" +
            "    }" +
            "    .blog-meta a {" +
            "      color: #27ae60;" +
            "    }" +
            "    .blog-pagination a {" +
            "      font-size: 1.4rem;" +
            "    }" +
            "    .blog-team li {" +
            "      padding: 4px;" +
            "    }" +
            "    .blog-team img {" +
            "      margin-bottom: 0;" +
            "    }" +
            "    .blog-content img," +
            "    .blog-team img {" +
            "      max-width: 100%;" +
            "      height: auto;" +
            "    }" +
            "    .blog-content > p {" +
            "      line-height: 30px;" +
            "    }" +
            "    .blog-footer {" +
            "      padding: 10px 0;" +
            "      text-align: center;" +
            "    }" +
            "img {" +
            "      display: inline-block;" +
            "      max-width: 100%;" +
            "      height: auto;" +
            "    }" +
            "  </style>" +
            "</head>";

    public static String PAGE_ARTICLE_TITLE_PRE = "<body>" +
            "<article class=\"blog-main\">" +
            "<h2 class=\"blog-title\">" +
            "<a href=\"#\">";

    public static String PAGE_ARTICLE_TITLE_POST = "</a>" +
            "      </h2>";

    public static String PAGE_ARTICLE_ACCOUNT_PRE = "<h4 class=\"blog-meta\">by";

    public static String PAGE_ARTICLE_ACCOUNT_POST = " </h4>";

    public static String PAGE_ARTICLE_CONTENT_PRE = "<div class=\"blog-content\">" +
            "            <p>";

    public static String PAGE_ARTICLE_CONTENT_POST = "</p>" +
            "      </div>" +
            "    </article>" +
            "</body>" +
            "</html>";
}
