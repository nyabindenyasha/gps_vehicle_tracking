package com.minimum.contrroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.minimum.local.ActionResult;
import com.minimum.local.VehicleResponse;
import com.minimum.model.RfidEkombiKombiTracker;
import com.minimum.service.RfidEkombiKombiTrackerService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/vehicleTracker")
public class RfidEkombiKombiTrackerController {

	@Autowired
	RfidEkombiKombiTrackerService kombiTrackerService;

	@ApiOperation(value = "", response = Iterable.class)
	@PostMapping()
	public ResponseEntity<ActionResult> save(@Valid @RequestBody RfidEkombiKombiTracker kombiTracker) {
		ActionResult result = new ActionResult();
		try {
			kombiTracker.setTime(new Date());
			kombiTrackerService.save(kombiTracker);
			result.setMessage("Success");
			return ResponseEntity.ok().body(result);
		} catch (Exception exception) {
			result.setMessage(exception.getMessage());
			return new ResponseEntity<ActionResult>(result, HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/querry")
	public ResponseEntity<ActionResult> saveUsingQuerry(@RequestParam("vehicleId") int kombiId,
			@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
		ActionResult result = new ActionResult();
		try {
			RfidEkombiKombiTracker rfidEkombiKombiTracker = new RfidEkombiKombiTracker();
			rfidEkombiKombiTracker.setTime(new Date());
			rfidEkombiKombiTracker.setCurrentLocationLatitude(latitude);
			rfidEkombiKombiTracker.setCurrentLocationLongitude(longitude);
			rfidEkombiKombiTracker.setVehicleId(kombiId);
			kombiTrackerService.save(rfidEkombiKombiTracker);
			result.setMessage("Success");
			return ResponseEntity.ok().body(result);
		} catch (Exception exception) {
			result.setMessage(exception.getMessage());
			return new ResponseEntity<ActionResult>(result, HttpStatus.BAD_GATEWAY);
		}
	}

	@ApiOperation(value = "", response = Iterable.class)
	@GetMapping()
	public ResponseEntity<Iterable<VehicleResponse>> findAll() {
		try {
			Iterable<RfidEkombiKombiTracker> rfidEkombiKombiTrackerList = kombiTrackerService.findAll();
			List<VehicleResponse> vehicleResponseView = new ArrayList<>();

			for (RfidEkombiKombiTracker rfidEkombiKombiTrackerTemp : rfidEkombiKombiTrackerList) {
				VehicleResponse vehicleResponse = new VehicleResponse();
				vehicleResponse.setId(rfidEkombiKombiTrackerTemp.getId());
				vehicleResponse.setCurrentLocationLatitude(rfidEkombiKombiTrackerTemp.getCurrentLocationLatitude());
				vehicleResponse.setCurrentLocationLongitude(rfidEkombiKombiTrackerTemp.getCurrentLocationLongitude());
				vehicleResponse.setTime(rfidEkombiKombiTrackerTemp.getTime().toString());
				vehicleResponse.setVehicleId(rfidEkombiKombiTrackerTemp.getVehicleId());
				vehicleResponseView.add(vehicleResponse);
			}

			return ResponseEntity.ok().body(vehicleResponseView);
		} catch (Exception exception) {
			Iterable<VehicleResponse> iterable = null;
			return new ResponseEntity<Iterable<VehicleResponse>>(iterable, HttpStatus.BAD_GATEWAY);
		}
	}

	@ApiOperation(value = "", response = Iterable.class)
	@GetMapping("{id}")
	public ResponseEntity<Optional<RfidEkombiKombiTracker>> findOne(@PathVariable int id) {
		Optional<RfidEkombiKombiTracker> kombiTracker = kombiTrackerService.findOne(id);
		if (!kombiTracker.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(kombiTracker);
	}

	@ApiOperation(value = "", response = Iterable.class)
	@DeleteMapping("{id}")
	public ResponseEntity<ActionResult> delete(@PathVariable int id) {
		ActionResult result = new ActionResult();
		if (kombiTrackerService.findOne(id) != null) {
			kombiTrackerService.delete(id);
			result.setMessage("Success");
			return ResponseEntity.ok().body(result);
		}
		result.setMessage("Cannot delete the RfidEkombiKombiTracker");
		return new ResponseEntity<ActionResult>(result, HttpStatus.BAD_REQUEST);
	}

}
