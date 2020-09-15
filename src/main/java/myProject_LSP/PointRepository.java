package myProject_LSP;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends PagingAndSortingRepository<Point, Long>{
    Optional<Point> findByOrderId(Long orderId);


}