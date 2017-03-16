package cn.zzuzl.xblog.model.vo;

public class NewArticleVO {
    private int articleId;
    private int userId;
    private String articleTitle;
    private String description;
    private String userUrl;
    private String nickname;

    public NewArticleVO(int articleId, int userId, String articleTitle, String description, String userUrl, String nickname) {
        this.articleId = articleId;
        this.userId = userId;
        this.articleTitle = articleTitle;
        this.description = description;
        this.userUrl = userUrl;
        this.nickname = nickname;
    }

    public NewArticleVO() {
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
