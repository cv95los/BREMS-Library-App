import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {DropdownNotClosableZone} from "./dropdown-not-closable.feature";
import {Dropdown} from "./dropdown.feature";
import {DropdownOpen} from "./dropdown-open.feature";

export * from "./dropdown-not-closable.feature";
export * from "./dropdown.feature";
export * from "./dropdown-open.feature";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    DropdownNotClosableZone,
    Dropdown,
    DropdownOpen,
  ],
  exports: [
    DropdownNotClosableZone,
    Dropdown,
    DropdownOpen,
  ]
})

export class DropdownModule {

}
