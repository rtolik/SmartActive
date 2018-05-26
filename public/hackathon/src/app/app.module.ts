import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppComponent} from "./app.component";
import {RouterModule, Routes} from "@angular/router";
import {ChatComponent} from "./chat/chat.component";
import {ChatRoomComponent} from "./chat/chat-room/chat-room.component";
import {ChatSelectComponent} from "./chat/chat-select/chat-select.component";
import {HomeComponent} from "./home/home.component";
import {EventListComponent} from "./home/event-list/event-list.component";
import {EventOneComponent} from "./home/event-one/event-one.component";
import {FilterComponent} from "./home/event-list/filter/filter.component";
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {EventListOneElementComponent} from "./home/event-list/event-list-one-element/event-list-one-element.component";
import {HeaderComponent} from "./home/header/header.component";
import {MainComponent} from "./home/main/main.component";
import {HttpModule} from "@angular/http";
import {FilterOneComponent} from "./home/event-list/filter/filter-one/filter-one.component";
import {OneMessageComponent} from "./chat/chat-room/one-message/one-message.component";
import {CabinetComponent} from "./home/cabinet/cabinet.component";
import {LeftSideComponent} from "./home/cabinet/left-side/left-side.component";
import {ManageUsageComponent} from "./home/cabinet/manage-usage/manage-usage.component";
import {SaveUsageComponent} from "./home/cabinet/save-usage/save-usage.component";
import {SaveMessageComponent} from "./home/cabinet/save-message/save-message.component";
import {SettingsComponent} from "./home/cabinet/settings/settings.component";
import {ManageUsageOneComponent} from "./home/cabinet/manage-usage/manage-usage-one/manage-usage-one.component";
import {SaveUsageOneComponent} from "./home/cabinet/save-usage/save-usage-one/save-usage-one.component";
import {ActiveGuard} from "../environments/service/can-active/auth-guard";
import {ActiveGuardService} from "../environments/service/can-active/auth-guard-service";


const routes: Routes = [
  {
    path: 'chat', component: ChatComponent/*, canActivate: [ActiveGuard]*/, children: [
    {path: ':id', component: ChatRoomComponent},
    {path: '', component: ChatSelectComponent}
  ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: RegistrationComponent},
  {
    path: '', component: HomeComponent, children: [
    {path: '', component: MainComponent},
    {path: 'list', component: EventListComponent},
    {path: ':id', component: EventOneComponent},
    {
      path: 'cabinet', component: CabinetComponent/*, canActivate: [ActiveGuard]*/, children: [
      {path: 'settings', component: SettingsComponent},
      {path: 'manage_usage', component: ManageUsageComponent},
      {path: 'save_usage', component: SaveUsageComponent},
      {path: 'save_message', component: SaveMessageComponent},
    ]
    }
  ]
  }
];


@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    ChatRoomComponent,
    ChatSelectComponent,
    HomeComponent,
    EventListComponent,
    EventOneComponent,
    FilterComponent,
    LoginComponent,
    RegistrationComponent,
    EventListOneElementComponent,
    HeaderComponent,
    MainComponent,
    FilterOneComponent,
    OneMessageComponent,
    CabinetComponent,
    LeftSideComponent,
    ManageUsageComponent,
    SaveUsageComponent,
    SaveMessageComponent,
    SettingsComponent,
    ManageUsageOneComponent,
    SaveUsageOneComponent
  ],
  imports: [
    HttpModule,
    BrowserModule,
    RouterModule.forRoot(routes, {useHash: true})
  ],
  providers: [ActiveGuard],
  bootstrap: [AppComponent]
})
export class AppModule {
}
