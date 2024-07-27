package bg.sofuni.mailsender.repository;

import bg.sofuni.mailsender.enity.MailHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface MailHistoryRepository extends JpaRepository<MailHistory, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM MailHistory mh WHERE mh.date < :olderThan")
    void deleteOldHistory(Instant olderThan);


    @Query("SELECT mh FROM MailHistory mh WHERE YEAR(mh.date) = YEAR(:date) AND DAYOFYEAR(mh.date) >= DAYOFYEAR(:date)")
    Page<MailHistory> findAllByDateGreaterThanEqual(Instant date, Pageable pageable);

    @Query("SELECT mh FROM MailHistory mh WHERE YEAR(mh.date) = YEAR(:date) AND  DAYOFYEAR(mh.date) = DAYOFYEAR(:date)")
    Page<MailHistory> findAllByDateEquals(Instant date, Pageable pageable);
}
