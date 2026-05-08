package com.breno.PriceRadar_API.controllers;

import com.breno.PriceRadar_API.services.TrackedItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Items/api/v1")
@AllArgsConstructor
public class ItemController {

    private final TrackedItemService trackedItemService;
}
