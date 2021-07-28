package ie.lyit.app.service.impl;

import ie.lyit.app.domain.File;
import ie.lyit.app.repository.FileRepository;
import ie.lyit.app.service.FileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link File}.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    /**
     * Constructor
     * @param fileRepository -
     */
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        return fileRepository.save(file);
    }

    @Override
    public Optional<File> partialUpdate(File file) {
        log.debug("Request to partially update File : {}", file);

        return fileRepository
            .findById(file.getId())
            .map(
                existingFile -> {
                    if (file.gets3FileKey() != null) {
                        existingFile.sets3FileKey(file.gets3FileKey());
                    }

                    return existingFile;
                }
            )
            .map(fileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<File> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return fileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<File> findOne(Long id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
