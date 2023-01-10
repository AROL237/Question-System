package org.signing237.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    @Lob
    private byte[] file;
}
