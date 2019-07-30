package softuni.exam.domain.dtos.jsonImport;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import softuni.exam.domain.entities.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Map;

import static softuni.exam.messages.ErrorMessages.I_Player;

public class PlayerDto {

    @Expose
    @NotNull
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @NotNull(message = I_Player)
    @Size(min = 3, max = 15, message = I_Player)
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @NotNull
    @Min(value = 3, message = I_Player)
    @Max(value = 99, message = I_Player)
    @SerializedName("number")
    private Integer number;

    @Expose
    @NotNull(message = I_Player)
    @Min(value = 0, message = I_Player)
    @SerializedName("salary")
    private BigDecimal salary;

    @Expose
    @NotNull(message = I_Player)
    @SerializedName("position")
    private Position position;

    @Expose
    @NotNull(message = I_Player)
    @SerializedName("picture")
    private Map<String, String> picture;

    @Expose
    @NotNull(message = I_Player)
    @SerializedName("team")
    private Map<Object, Object> team;

    public PlayerDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Map<String, String> getPicture() {
        return this.picture;
    }

    public void setPicture(Map<String, String> picture) {
        this.picture = picture;
    }

    public Map<Object, Object> getTeam() {
        return this.team;
    }

    public void setTeam(Map<Object, Object> team) {
        this.team = team;
    }
}
