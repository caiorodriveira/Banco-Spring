import { MatPaginator } from '@angular/material/paginator';
import { PessoasService } from './../../services/pessoas/pessoas.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { Component, ViewChild } from '@angular/core';
import { ContasService } from 'src/app/services/contas/contas.service';

@Component({
  selector: 'app-contas',
  templateUrl: './contas.component.html',
  styleUrls: ['./contas.component.css']
})
export class ContasComponent {
  contaForm: FormGroup;
  messageError: any;
  dataContas = new MatTableDataSource<any>()
  displayedColumns = ['nomePessoa', 'cpfPessoa', 'numeroConta', 'editar', 'remover'];
  contaAtual: any;
  pessoas: any;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private pessoaService: PessoasService,
    private contaService: ContasService,
  ) {
    this.contaForm = fb.group({
      id: [null],
      pessoa: ["", [Validators.required]],
      numeroConta: [null, [Validators.required]],
      saldo:[null, Validators.required]
    })

    this.contaAtual = {
      "id" : null,
      "pessoa":null,
      "numeroConta":null,
      "saldo": null
    }
  }

  ngAfterViewInit(): void {
    this.dataContas.paginator = this.paginator;
  }
  ngOnInit(): void {
    this.listarContas();
    this.listarPessoas();
  }

  salvarConta(conta: any) {
    this.messageError = null;
    conta.pessoa = {"id": conta.pessoa};
    this.contaService.save(conta).subscribe(
      (res) => {
        console.log(res);
        this.contaForm.reset();
        window.location.reload();
      },
      (error) => {
        this.messageError = {
          "erro": "Erro ao enviar as informações",
          "motivos": []
        }
        if(error.status == 406){
          this.messageError.motivos = ["digite um numero para a conta"]
        } else if (error.status == 400) {
          this.messageError.motivos = [
            "verifique os campos se estão preenchidos corretamente",
            "verifique se ja não há uma conta com esse numero cadastrada"
          ]
        }
      }
    )
  }

  listarContas(){
    this.contaService.getAll().subscribe(
      (res:any) => {
        let aux = res;
        aux.forEach((e:any) => {
          if (Number(e.pessoa)) {
            this.pessoaService.getById(e.pessoa).subscribe(
              pessoa => e.pessoa = pessoa
            )
          }
        });
        this.dataContas.data = aux;
      }
    )
  }
  listarPessoas(){
    this.pessoaService.getAll().subscribe(
      (res) => {
        this.pessoas = res;
      }
    )
  }

  confirmarRemocao(conta: any){
    this.contaAtual = conta;
  }

  removerConta(conta: any){
    console.log(conta)
    this.contaService.remove(conta.id).subscribe(
      () => {window.location.reload()},
      (error) =>{
        this.messageError = {
          "erro": "não foi possivel remover a conta de numero " + conta.numeroConta,
          "motivos": ["Verifique se a conta possui histórico de extrato, caso haja, não é possível remove-lá"]
        }
      }
    )
  }

  editarConta(conta:any){
    this.contaForm.patchValue({
      id: conta.id,
      pessoa: conta.pessoa.id,
      numeroConta: conta.numeroConta,
      saldo: conta.saldo
    })
  }

}
