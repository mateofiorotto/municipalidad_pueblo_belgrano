package ar.gob.pueblogeneralbelgrano.municipalidad.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("denyAll()")
@RequestMapping("/event")
public class EventController {


}
