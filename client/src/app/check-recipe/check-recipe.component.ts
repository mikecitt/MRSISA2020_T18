import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../service';

@Component({
  selector: 'app-check-recipe',
  templateUrl: './check-recipe.component.html',
  styleUrls: ['./check-recipe.component.css']
})
export class CheckRecipeComponent implements OnInit {

  recipes;

  constructor(private appointmentService: AppointmentService) { }

  ngOnInit(): void {
    this.appointmentService.getRecipes().subscribe(result => {
      console.log(result);
      this.recipes = result['data'];
    });
  }

  checkRecipe(aid, rid) {
    this.appointmentService.checkRecipe(aid, rid).subscribe(result => {
      this.appointmentService.getRecipes().subscribe(result => {
        this.recipes = result['data'];
      });
    });
  }

}
