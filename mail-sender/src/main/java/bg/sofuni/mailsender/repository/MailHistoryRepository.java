package bg.sofuni.mailsender.repository;

import bg.sofuni.mailsender.enity.MailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailHistoryRepository extends JpaRepository<MailHistory, Long> {

}
