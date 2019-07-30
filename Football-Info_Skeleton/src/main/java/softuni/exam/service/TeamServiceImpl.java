package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.xmlInport.TeamDto;
import softuni.exam.domain.dtos.xmlInport.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String XML_TEAMS_FILE_PATHE = "/Users/andreyivanov/Downloads/" +
            "Football-Info_Skeleton/src/main/resources/files/xml/teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, ModelMapper modelMapper, FileUtil fileUtil, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importTeams() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(TeamRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TeamRootDto list = (TeamRootDto) unmarshaller.unmarshal(new File(XML_TEAMS_FILE_PATHE));

        for (TeamDto teamDto : list.getTeamDtos()) {
            Team team = this.modelMapper.map(teamDto, Team.class);

            Picture picture = this.pictureRepository.findByUrl(teamDto.getPicture().getUrl());
            if (picture == null){
                sb.append(String.format("Invalid team\n"));
                continue;
            }

            team.setPicture(picture);

             if (!this.validatorUtil.isValid(teamDto)){
                this.validatorUtil.violations(teamDto)
                        .forEach(v -> sb.append(String.format("%s\n", v.getMessage())));

                continue;
            }

            this.teamRepository.saveAndFlush(team);
            sb.append(String.format("Successfully imported - %s\n", team.getName()));

        }


        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return this.fileUtil.readFile(XML_TEAMS_FILE_PATHE);
    }
}
