package com.breno.PriceRadar_API;

import com.breno.PriceRadar_API.services.AlertService;
import com.breno.PriceRadar_API.services.SnapshotService;
import com.breno.PriceRadar_API.services.TrackedItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class PriceRadarApiApplicationTests {

	@Autowired
	private TrackedItemService trackedItemService;

	@Autowired
	private SnapshotService snapshotService;

	@Autowired
	private AlertService alertService;

	@Test
	void contextLoads() {
		// Verifica que o contexto Spring carrega sem erros
	}

	@Test
	void serviceBeansAreLoaded() {
		assertNotNull(trackedItemService, "TrackedItemService should be loaded");
		assertNotNull(snapshotService, "SnapshotService should be loaded");
		assertNotNull(alertService, "AlertService should be loaded");
	}

}
