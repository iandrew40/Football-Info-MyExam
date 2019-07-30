package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.xmlInport.PictureDto;
import softuni.exam.domain.dtos.xmlInport.PictureRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String XML_PICTURE_FILE_PATH = "/Users/andreyivanov/Downloads/" +
            "Football-Info_Skeleton/src/main/resources/files/xml/pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(PictureRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PictureRootDto list = (PictureRootDto) unmarshaller.unmarshal(new File(XML_PICTURE_FILE_PATH));

        for (PictureDto pictureDto : list.getPictureDtos() ) {
            Picture picture = this.modelMapper.map(pictureDto, Picture.class);

            if (!this.validatorUtil.isValid(pictureDto)){
                this.validatorUtil.violations(pictureDto)
                        .forEach(v -> sb.append(String.format("%s\n", v.getMessage())));

                continue;
            }

            this.pictureRepository.saveAndFlush(picture);
            sb.append(String.format("Successfully imported picture - %s\n", picture.getUrl()));

        }

        return sb.toString();

    }

    @Override
    public boolean areImported() {

        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws JAXBException, IOException {
    return this.fileUtil.readFile(XML_PICTURE_FILE_PATH);    }

}
