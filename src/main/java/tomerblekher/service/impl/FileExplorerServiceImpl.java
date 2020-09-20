package tomerblekher.service.impl;

import tomerblekher.domain.Path;
import tomerblekher.service.FileExplorerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileExplorerServiceImpl implements FileExplorerService {

    private final Logger log = LoggerFactory.getLogger(FileExplorerServiceImpl.class);

    @Override
    public void listPath(Path path) {

    }
}
