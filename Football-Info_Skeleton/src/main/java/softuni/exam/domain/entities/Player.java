package softuni.exam.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static softuni.exam.messages.ErrorMessages.I_Player;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

  private String firstName;
  private String lastName;
  private Integer number;
  private BigDecimal salary;
  private Position position;
  private Picture picture;
  private Team team;


    public Player() {
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 15, message = I_Player)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "number", nullable = false)
    @Min(value = 1, message = I_Player)
    @Max(value = 99, message = I_Player)
    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "salary", nullable = false)
    @Min(value = 0, message = I_Player)
    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @NotNull(message = I_Player)
    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id", nullable = false)
    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
