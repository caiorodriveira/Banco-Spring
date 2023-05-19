import { MovimetacaoComponent } from './pages/movimetacao/movimetacao.component';
import { ContasComponent } from './pages/contas/contas.component';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '',  pathMatch: 'full', redirectTo: 'pessoas'},
  {path: 'pessoas', pathMatch: 'full', component: PessoasComponent},
  {path: 'contas', pathMatch: 'full', component: ContasComponent},
  {path: 'movimentacao', pathMatch: 'full', component: MovimetacaoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
