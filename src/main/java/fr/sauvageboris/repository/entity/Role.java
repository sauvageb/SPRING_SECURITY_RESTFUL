package fr.sauvageboris.repository.entity;

import fr.sauvageboris.dto.response.RoleDto;
import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    public Role() {
    }

    public Role(RoleEnum name) {
        this.roleName = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public RoleDto toDto() {
        return new RoleDto(id, roleName.name());
    }
}

