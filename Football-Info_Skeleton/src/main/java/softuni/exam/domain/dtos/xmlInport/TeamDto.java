package softuni.exam.domain.dtos.xmlInport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static softuni.exam.messages.ErrorMessages.I_Team;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDto {

    @XmlElement
    @NotNull(message = I_Team)
    @Size(min = 3, max = 20, message = I_Team)
    private String name;

    @NotNull
    @XmlElement(name = "picture")
    private PictureDto picture;

    public TeamDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureDto getPicture() {
        return this.picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
    }
}
