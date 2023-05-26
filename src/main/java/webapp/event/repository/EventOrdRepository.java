package webapp.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.event.dto.memofeventDTO;
import webapp.event.pojo.EventOrdVO;

import java.util.List;


@Repository
public interface EventOrdRepository extends JpaRepository<EventOrdVO, Integer> {
    // connection pool + similar to DAO(both support by extends Jpa repository)
    // program new DAO behavior here
    
    @Query(value = "SELECT * FROM boardgame.event_ord WHERE EVENT_NO= :eventno", nativeQuery = true)
    List<EventOrdVO> selectmembyevent(@Param("eventno") Integer eventno);
    
    @Query(value = "SELECT * FROM boardgame.event_ord WHERE MEM_NO= :memno", nativeQuery = true)
    List<EventOrdVO> selectbyeventbymem(@Param("memno") Integer memno);

    @Modifying
    @Transactional
    @Query(value = "delete FROM boardgame.event_ord where EVENT_NO= :eventno AND MEM_NO= :memno", nativeQuery = true)
    void deleevent(@Param("eventno") Integer eventno,@Param("memno")Integer memno);
    EventOrdVO findByEventnoAndMemNo(Integer event_no,Integer mem_no );
    @Query( "SELECT new webapp.event.dto.memofeventDTO( e.eventNo,e.eventName,e.eventStarttime,e.eventFee,m.memNo)"+
            " FROM Event e JOIN EventOrdVO m ON e.eventNo = m.eventno"+ " WHERE m.memNo = :memno")
    List<memofeventDTO> findAllBymemno(@Param("memno") Integer memno);
}
