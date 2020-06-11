import { Component, OnInit } from '@angular/core';
import { ClinicService, Clinic, LocationService } from '../service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import Map from 'ol/Map';
import View from 'ol/View';
import VectorLayer from 'ol/layer/Vector';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import OSM from 'ol/source/OSM';
import * as olProj from 'ol/proj';
import TileLayer from 'ol/layer/Tile';
import Vector from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';

@Component({
  selector: 'app-clinic-profile',
  templateUrl: './clinic-profile.component.html',
  styleUrls: ['./clinic-profile.component.css']
})
export class ClinicProfileComponent implements OnInit {
  clinicProfileForm = this.fb.group({
				id: [{value: '', disabled: true}],
				name: [''],
				address: [''],
		    description: [''],
        clinicGrade: [''],
        patientGrade: ['']
		  });
  map: Map;

	close = false;

  constructor(private service: ClinicService, private locationService: LocationService, private fb: FormBuilder, public modal: NgbActiveModal) {

  }

  ngOnInit(): void {
  	this.service.getClinicProfile().subscribe(result2 => {
  		this.clinicProfileForm.setValue(result2);




      this.locationService.getLocationDetails(result2.address).subscribe(result => {
        var styleMarker = new Style({
          image: new Icon({
            scale: .7, anchor: [0.5, 1],
            src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABBBJREFUeNrkW01IVFEUPuMMk5qlaEkgKKERiBGG/dAPjrtmRmibJQWRmxZRbSJooWRE7ZptYVBUqyhJp0WQShFZRkISRklgSfZjaKiNkzPTOeNrEY1273v3nvvMA4fL6Dnnnvt937sz980bTyqVgqVsWbDEzWcnyePxSMUHQ2EvDjvR69C3oK9HX4Oea4VMo4+iv0Z/it6F/uhetDMhM48dNXtsJQkCgAsvxeEYeiP6aslpvqBfQ7+IQAwvKgBw4UU4tKI3oXsdqpRUcAn9NAIx5noAcPF7cGhDL1R8uX5DP4QgtLsSAFw4/fEc+knN+9Z59FMIRMo1AODi6d3kKvp+ps37OvoBBCHpFABVb4MRxsWDNVdERSHHCkD2D1ublAlrQhVcNnYJ4OLLcXiJnmMIgB/oGxCEIVOXQMTg4sGaO2LkEkD2A9YnNjdYHaqg285afA4mPW4nyYsfiyrL41BVEYeigrlNfGw8Cwbe+uHVkB8SCdu9dLMpIBSuL8ZhRAZA2jZqa2Kwr34SVhVkXuXXcS/c6MiDnr5skGxrFr0k2tnxmWsPCMos3udNwYmDE3C0cWLexZPR/yiGYilHUslBzuNwQCb4SMN32FEdE46nWMrR2ZNTADaKBm6qnIHA5pg8wphDuTp6UgFAmWjg3uCU7V1WMreMEwChk15xYQIqSn/aBoByqYbKnnSdBTLa2pJZV9TQAYBQV/krk44blKgxywnAiEjQ1LTHMQASNUY4ARgUCXo/6nMMgESNQU4AnogEDX/0wacx+7cEKZdqqOxJFQBR0cBb95fbBkAyN8oGAJ686N59j0jsg96c9CFH1iiHcgWtx+qJ9W2wWSQoiZv4hbZ8ePdBfD+gWMpJJtX2ovR+QPpEFArTEbRWJCfbn4KG8CSEdk2nj8SZjI7C0Ye5cLMzD2Jxjwz7Abt3hJxu02dEAaAFXbm9Au525cL26hmoWheHovy5T3ljE14YeOOHxy+WpY/EOpSoRQGWCmj33WroTlAvsr/t9wtTt8WbwZw5nlvVFyMmVPAH+yYVYEoFSuZU+d0gpwr+Yt+0ArhVoGwupV+PM6kgI/tuUACXCpTOoeMBCZ0qmJd9tyhAtwqU19b1jNAzHGoU9/oc2V+wplsUQHZWQ81WHY3qAoAeZOpXWK/fqrk4ALAeYGpRWLIl00NRblaAShVoY18rAApVoI193QpQoQKt7GsHQIEKtLLPoQAnKtDOPgsADlSgnX0uBdhRAQv7bADYUAEL+5wKkFEBG/usAEiogI19bgWIqICVfXYABFTAyr4JBSykAnb2jQCwgArY2TelgEwqMMK+MQAyqMAI+2Q+MGfEeJ/VQ7upJrT+cvRfFgyFd+OQjezfUVGP7TdD/5Mt+V+P/xJgAO0Axv5zXgpBAAAAAElFTkSuQmCC'
          })
        });

        var coord1 = [result[0].lon, result[0].lat];
        var marker1 = new Point(coord1);
        var featureMarker1 = new Feature(marker1);

        var vector = new Vector({
          source: new Vector({
            features: [featureMarker1],
            //wrapX: false
          }),
          style: [styleMarker],
        });
        console.log(result);
        this.map = new Map({
          target: 'mapa',
          layers: [
            new TileLayer({
              source: new OSM()
            }),
            vector
          ],
          view: new View({
            center: olProj.fromLonLat([result[0].lon, result[0].lat]),
            zoom: 17
          })
        });
      })
  	})

  }

  updateClinicProfile() {
  	let payload = this.clinicProfileForm.getRawValue();

  	this.service.updateClinicProfile(payload).subscribe(result => {
  		this.modal.dismiss('cancel click')
  	})
  }

  closeDialog() {
  	this.close = true;
  	setTimeout(() => {
  		this.modal.dismiss('cancel click');
  	}, 300);
  }

  onMapReady(map: Map) {
    setTimeout(function() {
      map.invalidateSize();
    }, 10);

      console.log(map);
  }
}
