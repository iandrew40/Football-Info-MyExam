package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.jsonImport.PlayerDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String JSON_PLAYERS_FILE_PATH = "/Users/andreyivanov/Downloads/" +
            "Football-Info_Skeleton/src/main/resources/files/json/players.json";

    private final PlayerRepository playerRepository;
    private final PictureRepository pictureRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final Gson gson;


    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PictureRepository pictureRepository, TeamRepository teamRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.pictureRepository = pictureRepository;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();

        PlayerDto[] dtos = this.gson.fromJson(readPlayersJsonFile(), PlayerDto[].class);

        PlayerDtoLoop:
        for (PlayerDto dto : dtos) {
            Player player = this.modelMapper.map(dto, Player.class);


            Map<String, String> pictureResult = dto.getPicture();
            for (String s : pictureResult.values()) {
                Picture picture = this.pictureRepository.findByUrl(s);

                if(picture == null){
                    continue PlayerDtoLoop;
                }
                player.setPicture(picture);
                break;
            }

            Map<Object, Object> teamResult = dto.getTeam();
            for (Object value : teamResult.values()) {
                Team team = this.teamRepository.findByName(value.toString());

                if(team == null){
                    continue PlayerDtoLoop;
                }
                player.setTeam(team);
                break;
            }


            if (!this.validatorUtil.isValid(player)){
                this.validatorUtil.violations(player)
                        .forEach(p -> sb.append(String.format("%s\n", p.getMessage())));
                continue;
            }


            this.playerRepository.saveAndFlush(player);
            sb.append(String.format("Successfully imported player: %s %s\n",
                    player.getFirstName(), player.getLastName()));

        }

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(JSON_PLAYERS_FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        Collection<Player> players = this.playerRepository.findPlayers();

        for (Player player : players) {
            sb.append(String.format("Player name: %s %s\n", player.getFirstName(), player.getLastName()))
                    .append(String.format("Number: %d\n", player.getNumber()))
                    .append(String.format("Salary: %s\n", player.getSalary().toString()))
                    .append(String.format("Team: %s\n", player.getTeam().getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        Collection<Player> players = this.playerRepository.findAllPlayersByTeam();

        for (Player player : players) {
            sb.append(String.format("Team: %s\n", player.getTeam().getName()))
                    .append(String.format("Player name: %s %s - %s\n", player.getFirstName(), player.getLastName(),
                            player.getPosition().toString()))
                    .append(String.format("Number: %d\n", player.getNumber()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
