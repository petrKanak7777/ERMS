package cz.erms.backend.ejb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "file_id", unique = true, length = 36, nullable = false)
    private UUID id;

    @Column(name = "user_id", length = 36, nullable = false)
    @JsonProperty("user_id")
    private UUID userId;

    @Column(name = "name", length = 256, nullable = false)
    private String name;
}
