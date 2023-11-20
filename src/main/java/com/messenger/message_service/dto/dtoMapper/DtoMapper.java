package com.messenger.message_service.dto.dtoMapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DtoMapper<Model, Request, Response> {
    Model toModel(Request request);
    Response toResponse(Model model);

    List<Response> toResponseList(List<Model> models);
}
