package f3f.domain.bookmark.dao;

import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository  extends JpaRepository<Scrap, Long> {

    @Modifying
    @Query(value = "INSERT INTO bookmark(follower_id, following_id)\n" +
            "SELECT * FROM (SELECT " +
            ":follower_id AS follower_id, " +
            ":following_id AS following_id) AS new_value\n" +
            "WHERE NOT EXISTS ( SELECT bm.follower_id,bm.following_id FROM bookmark bm WHERE bm.follower_id = :follower_id \n" +
            " and bm.following_id = :following_id) LIMIT 1;",nativeQuery = true)
    void saveFollowRequest(@Param("follower_id")Long follower_id, @Param("following_id")Long following_id);

}
