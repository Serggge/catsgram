package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.model.Follow;
import ru.yandex.practicum.catsgram.model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FollowDaoImpl implements FollowDao {

    private final JdbcTemplate jdbcTemplate;

    public FollowDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> getFollowFeed(String userId, int max) {
        String sqlForFollowers = "select user_id, follower_id from cat_follow where user_id=?";
        List<Follow> followers = jdbcTemplate.query(sqlForFollowers, (rs, rowNum) -> makeFollow(rs), userId);
        String sqlForPosts = "select * from cat_post where author_id in (?)";
        List<Post> followersPosts = jdbcTemplate.query(sqlForPosts, (rs, rowNum) -> makePost(rs),
                followers.stream()
                        .map(Follow::getFollowerId)
                        .collect(Collectors.toList()))
                .stream()
                .sorted((x, y) -> y.getCreationDate().compareTo(x.getCreationDate()))
                .limit(max)
                .collect(Collectors.toList());
        return null;
    }

    private Follow makeFollow(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");
        String followerId = rs.getString("follower_id");
        return new Follow(userId, followerId);
    }

    private Post makePost(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String author = rs.getString("author_id");
        String description = rs.getString("description");
        String photoUrl = rs.getString("photo_url");
        LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
        return new Post(id, author, description, photoUrl, creationDate);
    }
}
