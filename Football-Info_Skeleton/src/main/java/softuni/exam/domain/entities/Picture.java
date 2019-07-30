package softuni.exam.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    private String url;
    private List<Team> teams;
    private List<Player> players;

    public Picture() {
    }

    @Column(name = "url", nullable = false)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToMany(mappedBy = "picture")
    public List<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @OneToMany(mappedBy = "picture")
    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
