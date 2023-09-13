package fr.sauvageboris.dto.response;

import java.util.List;

public class UserDto {

    private Long id;
    private String username;
    private List<RoleDto> roleList;

    public UserDto() {
    }

    public UserDto(Long id, String username, List<RoleDto> roleList) {
        this.id = id;
        this.username = username;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RoleDto> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDto> roleList) {
        this.roleList = roleList;
    }
}

