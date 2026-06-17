package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.ScreenDTO;
import com.movie.movie_service.Entity.Screen;
import com.movie.movie_service.Entity.Theatre;
import com.movie.movie_service.ExceptionHandler.ResourceNotFoundException;
import com.movie.movie_service.Mapper.ScreenMapper;
import com.movie.movie_service.Repository.ScreenRepository;
import com.movie.movie_service.Repository.TheatreRepository;
import com.movie.movie_service.RequestDTO.ScreenRequestDTO;
import com.movie.movie_service.Service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScreeServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenMapper screenMapper;

    @Override
    @Transactional
    public ScreenDTO createScreen(ScreenRequestDTO requestDTO) {
        Theatre theatre = theatreRepository.findByIdAndIsActiveTrue(requestDTO.getTheatreId())
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + requestDTO.getTheatreId()));

        Screen screen = new Screen();
        screenMapper.convertRequestDTOToEntity(requestDTO, screen);
        screen.setTheatre(theatre);

        Screen savedScreen = screenRepository.save(screen);
        return screenMapper.convertEntityToDTO(savedScreen);
    }

    @Override
    public ScreenDTO getScreenById(Long id) {
        Screen screen = screenRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + id));
        return screenMapper.convertEntityToDTO(screen);
    }

    @Override
    @Transactional
    public void deleteScreen(Long id) {
        Screen screen = screenRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + id));
        screenRepository.delete(screen);
    }

    @Override
    @Transactional
    public ScreenDTO updateScreen(Long id, ScreenRequestDTO requestDTO) {
        Screen screen = screenRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + id));

        if (requestDTO.getTheatreId() != null && !requestDTO.getTheatreId().equals(screen.getTheatre().getId())) {
            Theatre theatre = theatreRepository.findByIdAndIsActiveTrue(requestDTO.getTheatreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + requestDTO.getTheatreId()));
            screen.setTheatre(theatre);
        }

        screenMapper.convertRequestDTOToEntity(requestDTO, screen);

        Screen updatedScreen = screenRepository.save(screen);
        return screenMapper.convertEntityToDTO(updatedScreen);
    }

    @Override
    public Page<ScreenDTO> getScreensByTheatre(Long theatreId, int pageNo, int pageSize) {
        theatreRepository.findByIdAndIsActiveTrue(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found with id: " + theatreId));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Screen> screensPage = screenRepository.findByTheatreIdAndIsActiveTrue(theatreId, pageable);

        return screensPage.map(screenMapper::convertEntityToDTO);
    }
}