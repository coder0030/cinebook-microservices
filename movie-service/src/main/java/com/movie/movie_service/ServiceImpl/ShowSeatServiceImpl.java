package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.ShowSeatDTO;
import com.movie.movie_service.Entity.Show;
import com.movie.movie_service.Entity.ShowSeat;
import com.movie.movie_service.ExceptionHandler.ResourceNotFoundException;
import com.movie.movie_service.Mapper.ShowSeatMapper;
import com.movie.movie_service.Repository.ShowRepository;
import com.movie.movie_service.Repository.ShowSeatRepository;
import com.movie.movie_service.Service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowSeatServiceImpl implements ShowSeatService {

    private final ShowSeatMapper showSeatMapper;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;


    @Override
    public ShowSeatDTO getShowSeatById(Long id) {
        ShowSeat showSeat = showSeatRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found with id: " + id));
        return showSeatMapper.convertEntityToDTO(showSeat);
    }

    @Override
    public Page<ShowSeatDTO> getShowSeatsByShow(Long showId, int pageNo, int pageSize) {
        Show show = showRepository.findByIdAndIsActiveTrue(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ShowSeat> showSeatsPage = showSeatRepository.findByShow_IdAndIsActiveTrue(showId, pageable);

        return showSeatsPage.map(showSeatMapper::convertEntityToDTO);
    }

    @Override
    public Page<ShowSeatDTO> getAvailableShowSeatsByShow(Long showId, int pageNo, int pageSize) {
        Show show = showRepository.findByIdAndIsActiveTrue(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ShowSeat> showSeatsPage = showSeatRepository.findByShow_IdAndSeat_IsBookedFalse(showId, pageable);

        return showSeatsPage.map(showSeatMapper::convertEntityToDTO);
    }

    @Override
    public ShowSeatDTO updateShowSeatPrice(Long id, Double price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }

        ShowSeat showSeat = showSeatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found with id: " + id));

        showSeat.setPrice(price);

        ShowSeat updatedShowSeat = showSeatRepository.save(showSeat);
        return showSeatMapper.convertEntityToDTO(updatedShowSeat);
    }

    @Override
    public void deleteShowSeat(Long id) {
        ShowSeat showSeat = showSeatRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSeat not found with id: " + id));
        showSeat.setIsActive(false);
        showSeatRepository.save(showSeat);
    }
}
