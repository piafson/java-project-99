package hexlet.code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "task_statuses")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class TaskStatus implements BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    private String slug;

    @CreatedDate
    private LocalDate cratedAt;
}
