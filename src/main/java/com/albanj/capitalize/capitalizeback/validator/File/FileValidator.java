package com.albanj.capitalize.capitalizeback.validator.File;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class FileValidator implements ConstraintValidator<FileDtoValidation, FileDto> {

    private final FileRepository repo;

    @Autowired
    public FileValidator(FileRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public boolean isValid(FileDto fileDto, ConstraintValidatorContext constraintValidatorContext) {

        return isNotNull(fileDto) && !containsParentDirectory(fileDto) && isPathValid(fileDto)
                && existsIfIdNotNull(fileDto);
    }

    private boolean isNotNull(FileDto fileDto) {
        return fileDto != null;
    }

    private boolean containsParentDirectory(FileDto fileDto) {
        return fileDto.getPath().contains("..");
    }

    private boolean isPathValid(FileDto fileDto) {
        try {
            Paths.get(fileDto.getPath());

        } catch (InvalidPathException e) {
            return false;
        }
        return true;
    }

    private boolean existsIfIdNotNull(FileDto fileDto) {
        if (fileDto.getId() != null) {
            File file = this.repo.getOne(fileDto.getId());
            if (file == null) {
                return false;
            }
        }
        return true;
    }

}
