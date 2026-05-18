package com.usermanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Paginated list wrapper for user collection endpoints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedUserResponse {

    private List<UserResponse> users;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
