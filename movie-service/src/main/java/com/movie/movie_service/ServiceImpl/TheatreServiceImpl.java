package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.TheatreDTO;
import com.movie.movie_service.Entity.Theatre;
import com.movie.movie_service.ExceptionHandler.TheatreAlreadyExistsException;
import com.movie.movie_service.ExceptionHandler.TheatreNotFoundException;
import com.movie.movie_service.Mapper.TheatreMapper;
import com.movie.movie_service.Repository.TheatreRepository;
import com.movie.movie_service.RequestDTO.TheatreRequestDTO;
import com.movie.movie_service.Service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final TheatreMapper theatreMapper;

    private void checkUniqueValidation(String name, String city, String state, Long id) {
        if (theatreRepository.existsByNameAndCityAndStateAndIsActiveTrueAndIdNot(
                name, city, state, id
        )) {
            throw new TheatreAlreadyExistsException(
                    String.format("Theatre with name '%s' in city '%s', state '%s' already exists. Please use different credentials.",
                            name, city, state)
            );
        }
    }

    private Theatre findTheatreById(Long id) {
        return theatreRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new TheatreNotFoundException(
                        String.format("Theatre not found with ID: %d. Either it doesn't exist or is inactive.", id)
                ));
    }

    @Transactional
    @Override
    public TheatreDTO createTheatre(TheatreRequestDTO requestDTO) {
         checkUniqueValidation(requestDTO.getName(), requestDTO.getCity(), requestDTO.getState(), null);
        Theatre theatre = new Theatre();
        theatre = theatreMapper.convertRequestDTOToEntity(requestDTO, theatre);
        return theatreMapper.convertEntityToDTO(theatreRepository.save(theatre));
    }

    @Override
    public TheatreDTO getTheatreById(Long id) {
        Theatre theatre = findTheatreById(id);
        return theatreMapper.convertEntityToDTO(theatre);
    }

    @Transactional
    @Override
    public TheatreDTO updateTheatre(Long id, TheatreRequestDTO requestDTO) {
        Theatre theatre = findTheatreById(id);
        String city = theatre.getCity();
        String name = theatre.getName();
        String state = theatre.getState();

        if(requestDTO.getCity() != null && !requestDTO.getCity().isBlank() && !requestDTO.getCity().equals(city)) {
            city = requestDTO.getCity();
        }

        if(requestDTO.getState() != null && !requestDTO.getState().isBlank() && !requestDTO.getState().equals(state)) {
            state = requestDTO.getState();
        }

        if(requestDTO.getName() != null && !requestDTO.getName().isBlank() && !requestDTO.getName().equals(name)) {
            name = requestDTO.getName();
        }

        checkUniqueValidation(name, city, state, theatre.getId());

        theatre.setName(name);
        theatre.setCity(city);
        theatre.setState(state);

        if(requestDTO.getAddress() != null && !requestDTO.getAddress().isBlank() &&
        !requestDTO.getAddress().equals(theatre.getAddress())) {
            theatre.setAddress(requestDTO.getAddress());
        }

        if(requestDTO.getTotalScreens() != null && !requestDTO.getTotalScreens()
                .equals(theatre.getTotalScreens())) {
            theatre.setTotalScreens(requestDTO.getTotalScreens());
        }

        return theatreMapper.convertEntityToDTO(theatreRepository.save(theatre));
    }

    @Transactional
    @Override
    public void deleteTheatre(Long id) {
        Theatre theatre = findTheatreById(id);
        theatre.setIsActive(false);
        theatreRepository.save(theatre);
    }

    @Override
    public Page<TheatreDTO> getAllTheatres(int pageNo, int pageSize) {
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Theatre> theatrePage = theatreRepository.findByIsActiveTrue(pageable);
        if(theatrePage.isEmpty()) {
            return Page.empty();
        }

        return theatrePage.map(theatreMapper::convertEntityToDTO);
    }

    @Override
    public Page<TheatreDTO> getTheatresByCity(String city, int pageNo, int pageSize) {
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Theatre> theatrePage = theatreRepository.findByCityAndIsActiveTrue(city,pageable);
        if(theatrePage.isEmpty()) {
            return Page.empty();
        }

        return theatrePage.map(theatreMapper::convertEntityToDTO);
    }
}
