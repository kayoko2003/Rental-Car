package Renter_Car.Repository;

import Renter_Car.Models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {
    List<Booking> findByCarIdAndEndDateAfterAndStatusNotIn(int carId, Timestamp currentDate, ArrayList<String> status);

    List<Booking> findByUserId(int userId);

    Page<Booking> findByUserId(int userId, Pageable pageable);

    int countByCarId(int carId);

    @Query("SELECT b.car.id, COUNT(b) FROM Booking b WHERE b.car.id IN :carIds GROUP BY b.car.id")
    List<Object[]> countBookingsByCarIds(List<Integer> carIds);

    @Query("SELECT b FROM Booking b WHERE b.car.id = :carId " +
            "AND (b.startDate BETWEEN :pickupTime AND :returnTime " +
            "OR b.endDate BETWEEN :pickupTime AND :returnTime " +
            "OR (b.startDate <= :pickupTime AND b.endDate >= :returnTime))" +
            "AND b.status NOT IN ('6', '7')")
    List<Booking> findConflictingBookings(int carId, Timestamp pickupTime, Timestamp returnTime);

//    @Query("SELECT b FROM Booking b WHERE b.startDate < :yesterday AND (b.status = 1 OR b.status = 2)")
//    List<Booking> findExpiredBookings(LocalDateTime yesterday);
}
