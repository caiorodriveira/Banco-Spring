import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MovimentacaoService } from './../../services/movimentacao/movimentacao.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ContasService } from 'src/app/services/contas/contas.service';
import { PessoasService } from './../../services/pessoas/pessoas.service';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-movimetacao',
  templateUrl: './movimetacao.component.html',
  styleUrls: ['./movimetacao.component.css']
})
export class MovimetacaoComponent implements OnInit{

  formMovimentacao: FormGroup;
  formPessoas: FormGroup;
  pessoas: any;
  contas: any;
  dataHistorico = new MatTableDataSource<any>()
  displayedColumns = ['data', 'valor'];
  idConta:number=0;
  messageError:any;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private pessoasService: PessoasService,
    private contasService: ContasService,
    private movimentacaoService: MovimentacaoService,
    private fb: FormBuilder
  ){
    this.formMovimentacao = this.fb.group({
      tipo: ["", [Validators.required]],
      valor: [null, [Validators.required]],
      idConta: ["", [Validators.required]],
      dataMovimentacao: [null, [Validators.required]],
    })

    this.formPessoas = this.fb.group({
      pessoas: ["", [Validators.required]]
    })

    this.messageError = {
      "erro" : null,
      "motivos": []
    }
  }

  ngAfterViewInit(): void {
    this.dataHistorico.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.listasPessoas();

    this.formPessoas.get('pessoas')?.valueChanges.subscribe((value) => {
      this.listarContasPorPessoa(value);
    })

    this.formMovimentacao.get('idConta')?.valueChanges.subscribe((value) => {
      if((value != null) && (value !=0)){
        this.listaHistorico(Number(value));
      }
    })
    this.formMovimentacao.valueChanges.subscribe(() => {
      this.messageError = {
        "erro" : null,
        "motivos": []
      }
      this.dataHistorico.data = [];
    })
  }

  listasPessoas(){
    this.pessoasService.getAll().subscribe(
      (res:any) => {
        this.pessoas = res
      },
      (error) => {
        console.error(error);
      }
    )
  }

  listarContasPorPessoa(idPessoa: number){
    this.contasService.getByPessoa(idPessoa).subscribe(
      (res:any) => {
        this.contas = res;
        this.formMovimentacao.controls['idConta'].setValue("");
      },
      (error) => {
        console.error(error);
      }
    )
  }

  listaHistorico(idConta: number){
    this.contasService.getExtrato(idConta).subscribe(
      (res:any)=>{
        this.dataHistorico.data = res;
      }
    )
  }

  salvarMovimentacao(movimentacao: any){
    movimentacao.dataMovimentacao = new Date();
    movimentacao.idConta = Number(movimentacao.idConta)
    if(movimentacao.tipo == "SAQUE"){
      this.movimentacaoService.saque(movimentacao).subscribe(
        (res) => {
          this.formMovimentacao.reset();
          this.formPessoas.reset();
          window.location.reload();
        },
        (error) => {
          console.log(error);
          this.messageError.erro = "Não foi possivel realizar o saque";
          this.messageError.motivos = [
            "verifique se tem saldo suficiente",
            "verifique se digitou o valor correto",
            "verifique se os dados estão corretos"
          ]
        }
      )
    } else if (movimentacao.tipo == "DEPOSITO"){
      this.movimentacaoService.deposito(movimentacao).subscribe(
        (res) => {
          this.formMovimentacao.reset();
          this.formPessoas.reset();
          window.location.reload();
        },
        (error) => {
          console.log(error);
          this.messageError.erro = "Não foi possivel realizar o depostio";
          this.messageError.motivos = [
            "verifique se digitou o valor correto",
            "verifique se os dados estão corretos"
          ]
        }
      )
    }
  }

}
