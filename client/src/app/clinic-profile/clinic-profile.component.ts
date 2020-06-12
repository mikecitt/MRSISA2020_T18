import { Component, OnInit } from '@angular/core';
import { ClinicService, Clinic, LocationService } from '../service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import Map from 'ol/Map';
import View from 'ol/View';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import OSM from 'ol/source/OSM';
import * as olProj from 'ol/proj';
import Tile from 'ol/layer/Tile';
import SourceVector from 'ol/source/Vector';
import LayerVector from 'ol/layer/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import {defaults} from 'ol/control';

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
  fail: string;
  clinicGrade: number = 0;

  constructor(private service: ClinicService, private locationService: LocationService, private fb: FormBuilder, public modal: NgbActiveModal) {

  }

  ngOnInit(): void {
    this.fail = null;
  	this.service.getClinicProfile().subscribe(result2 => {
      console.log(result2);
      this.clinicGrade = result2.clinicGrade;
  		this.clinicProfileForm.setValue(result2);

      this.locationService.getLocationDetails(result2.address).subscribe(result => {
        this.data = result[0];
        var styleMarker = new Style({
          image: new Icon({
            scale: .7, anchor: [0.5, 1],
            src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAAHdbkFIAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8WlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNi4wLWMwMDIgNzkuMTY0MzYwLCAyMDIwLzAyLzEzLTAxOjA3OjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjEuMSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTA2LTEyVDAxOjM3OjQzKzAyOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0wNi0xMlQwMTozOToxOCswMjowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0wNi0xMlQwMTozOToxOCswMjowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozNzUyNDZhYi0zMTk5LWFiNDYtODEzMS03NjhhNDNhMTk3N2UiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDowMzliMTMxMC1hZjQxLTM4NGYtOTNlNi03YTNkZmFiYWQ3NDEiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpkYmNiZDIwOS1mNGM5LWNmNDItOGU0ZC1kZjc2YzRiOTczMzgiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOmRiY2JkMjA5LWY0YzktY2Y0Mi04ZTRkLWRmNzZjNGI5NzMzOCIgc3RFdnQ6d2hlbj0iMjAyMC0wNi0xMlQwMTozNzo0MyswMjowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIxLjEgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDozNzUyNDZhYi0zMTk5LWFiNDYtODEzMS03NjhhNDNhMTk3N2UiIHN0RXZ0OndoZW49IjIwMjAtMDYtMTJUMDE6Mzk6MTgrMDI6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMS4xIChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6aK/e2AAAGX0lEQVR4nN2aTWwVVRTHfwOYCC50igILrEZJbMJGzKAkJQGCqbJQIO3KT6xOolbUskATo9G4KWyIgh9hIgoGV0WLIGoFFJKXiI6hCxZqAAPSRRHeaIzd0DIuOvP63sy9M/feN+8943/VOXPuOf/zvx9z7321wjAkC7PiP9ra2ggcr+Jt+65VLpeZERvil+HQtprnigNAuP+VVAor5pCbQoakw4y4tdDB9t1UzZayDlbXvhrPcLjbSqUIh7ZVdJCRlKcAsNb1pxwqEapLk5YpQkqo6tYg0KGtrQ2gpl8iTNq+OwugXC7LAyT1EiHWEBIq1Dgl9Pzl1xIdmwdTflIRrHX9YE0CMDFR0/hYjZ9IA0H9MU7YvrusWgMhg6TSEUZs312WNEpLSAQZsX13icgvcyBEQaSNQWE+5KGmG6sG0XvA0wnfG23fvQy1AylVQtQDycYAlwLH25E01gQIHG9lDuO+pCE5Er+N/6gdiT9irfskTvIh8ISQgRxLqx82VD8oBqjBG9IA1YPn4KFSxV691Ni++3p1G+lsfGDnIOxMmU8kDakSJPMgfqc+FwT4SWSUBRAxc5QDCD4iIzJaWSVUr+nS2Zi1HsQspNkhoxvjBKJvYjUy14N4eicRON4K4CVgBTAHOAt8DLxp++6kqE31EqBNIHA8C7gqdRRji+27L9dNIHC8cWC2ZvJqLLJ994wRAatr3xUE/Txv9UXGnt8BoV2xnf+9xC0b0x/OCPPD4e6LJgRSL8PP+sFql7cR7HGAcjjcPVf0Qn85zEgOcHpbj8j8t8w/j8CxpGHBnjG5t3WGRf3pbrB991ZpE4VB+D1wTzZPOeLVWTYIc7sgWsJT3wGd5FlQGgMmJFSSKxOoIjGi6K4cV2sWRF+VPBK5678xAQUSWsmNCGSQ0E5uTEBAwig5FLA9rxctJyDd0WRsRmYCrwKPArcB40wt2Vts300t3SBfBSFDgSSBwPHmAmPATGm0Kbxv++4zhRIIHG+AqS2YDioDM4tA3qaS6FSWOlgp4CpQ37cgcLzbDZPH7cfrIgCcNk0eYXbesVXaBYHjzZO9O7e9h/abO6cNVsD8t5/j4hFhk2+Aa2SxshTYIjKGQw/VJgcIbcY27pXFyRxnWQQeFJuXis1EG1ZNZBFIrUQ/bxVuOKeRs2HVJXAuaZgl7UlzZBH4KGkQ7XhrYF0qjkDyNmM6yRlpsAW7hedS4fchl4CssbV2BxMTpYRxkoOHSox9OpAKYPvuyqwEud+CjOtLFaSuOJNQ2REZnQlAfC2kTUAliARKxFX3hNoqqBLXORnpQJmwzq7YV3XUIaxD4G5FvxGNmFpnw1Ax+F0NIaAYfKShRzMFFbSq1yaQk0S7eiMCGSpoV29EQJLMqHpjAgIVjKo3JpBIalx9XQSipD51VA//geN5ywm0GrmHYxFkdxdJBI7XDrwIPALcpJnmD6Z+hHnL9t3zKg2ydn4yGI2ALAECx1sL7EJwrqoTZaDX9t39UodWCRA43gxgD/CwdjAz7AUes3235lc0EwHqWYYBCBzvKWCS5hVPlGsyyl0X6hIgcLwvAK9eEnXAizgYw3gKBI63H+kFjhxdfT1sbYdFC+G6OVN3Hf+MX+H0Bdh8Hobfybl9EONz23fXNm0NsLr2PY7g6kSGD57tofe+ORBK/4EgkeAku74e58l3tcTYEA5379ZpAOZT4AVVx1MDPfR2daoXDxAuoberk1MDObdxhpyqYSqAUjVHN/WwuKMz31GCxR2dHN2kLIKGwtMwFWBCxemODsPoZjGUOCVhKsARFaffzhpGN4uhxCkJUwFeU3FaPjDI6Ggp31GC0dESyweUF0IlTkmYHoh/AFap+C7sG+TA8RKEF9QThBc4cLzEwj7l4ldFnLRR11Y4cLzVwGGdtoc39nDnEpjbdgOE10cs/uJy+U9GTsK927X3Aats3/0OWnQWCBzvfuBL7SDFYI3tu1/FDy05C0QE1tQbxwA1xZuibgGgJSIUUjwUJAA0VYTCiocCBYCmiFBo8VCwAFARYX3RcYH1RRcPDRAAwPbdIYoVYX0Us3A0RAAoVISGFQ8NFAAKEaGhxUODBYC6RGh48dAEAcBIhKYUD00SALREaFrx0EQBQEmEphYPTRYAMkVoevHQAgFAKEJLiocG/Daog+gofW1RxTftPuD/hH8BngCHND6i+RAAAAAASUVORK5CYII='
          })
        });
        console.log(result);
        if(result.length != 1) {
          document.getElementById('mapa').style.height = '0px';
          this.fail = 'Nije moguće prikazati unetu adresu na mapi.';
          return;
        }
        this.map = new Map({
          target: 'mapa',
          controls: defaults({ attribution: false }),
          layers: [
            new Tile({
              source: new OSM()
            })
          ],
          view: new View({
            center: olProj.fromLonLat([result[0].lon, result[0].lat]),
            zoom: 17,
            maxZoom: 19
          })
        });

        var vector = new LayerVector({
          source: new SourceVector({
            features: [
              new Feature({
                 geometry: new Point(olProj.fromLonLat([result[0].lon, result[0].lat]))
             })
            ]
          }),
          style: [styleMarker]
        });
        this.map.addLayer(vector);

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
