/* eslint-disable @next/next/no-img-element */
'use client';
import { Button } from 'primereact/button';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dialog } from 'primereact/dialog';
import { FileUpload } from 'primereact/fileupload';
import { InputText } from 'primereact/inputtext';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import { classNames } from 'primereact/utils';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { Projeto } from '@/types';
import { ResourceService} from '../../../../service/ResourceService';

/* @todo Used 'as any' for types here. Will fix in next version due to onSelectionChange event type issue. */
const Resource = () => {
    let emtyResource: Projeto.Resource = {
        id: 0,
        name: '',
        key: ''
    };

    const [resources, setResources] = useState<Projeto.Resource[]>();
    const [resourceDialog, setResourceDialog] = useState(false);
    const [deleteResourceDialog, setDeleteResourceDialog] = useState(false);
    const [deleteResourcesDialog, setDeleteResourcesDialog] = useState(false);
    const [resource, setResource] = useState<Projeto.Resource>(emtyResource);
    const [selectedResources, setSelectedResources] = useState<Projeto.Resource[]>();
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState('');
    const toast = useRef<Toast>(null);
    const dt = useRef<DataTable<any>>(null);
    const resourceService = useMemo(() => new ResourceService(), []);

    useEffect(() => {
    //    ProductService.getProducts().then((data) => setUsers(data as any));
    if(!resources)
        resourceService.listarTodos().then((response) => {
        console.log(response.data);
        setResources(response.data);
        setResources([]);
    }).catch((setResources) => {
        console.log(error);
    })
}, [resourceService, resources]);


    const openNew = () => {
        setResource(emtyResource);
        setSubmitted(false);
        setResourceDialog(true);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setResourceDialog(false);
    };

    const hideDeleteResourceDialog = () => {
        setDeleteResourceDialog(false);
    };

    const hideDeleteResourcesDialog = () => {
        setDeleteResourcesDialog(false);
    };

   const saveResource = () => {
        setSubmitted(true);

        if (!resource.id) {
            resourceService.inserir(resource.id)
                .then((response) => {
                    setResourceDialog(false);
                    setResource(emtyResource);
                    setResources([]);
                    toast.current?.show({
                        severity: 'info',
                        summary: 'Sucesso!',
                        detail: 'Usuário cadastrado com sucesso!'
                    });
                }).catch((error) => {
                    console.log(error.data.message);
                    toast.current?.show({
                        severity: 'error',
                        summary: 'Erro!',
                         detail: 'Erro ao salvar!'
                    })
                });
        } else {
            resourceService.alterar(resource)
                .then((response) => {
                    setResourceDialog(false);
                    setResource(emtyResource);
                    setResources([]);
                    toast.current?.show({
                        severity: 'info',
                        summary: 'Sucesso!',
                        detail: 'Usuário alterado com sucesso!'
                    });
                }).catch((error) => {
                    console.log(error.data.message);
                    toast.current?.show({
                        severity: 'error',
                        summary: 'Erro!',
                        detail: 'Erro ao alterar!'
                    })
                })
        }
    };

    const editResource= (resource: Projeto.Resource) => {
        setResource({ ...resource });
        setResourceDialog(true);
    };
    
    const confirmDeleteResource = (resource: Projeto.Resource) => {
        setResource(resource);
        setDeleteResourceDialog(true);
    };
    
    const deleteResource = () => {
        if (resource.id) {
            resourceService.excluir(resource.id).then((response) => {
                setResource(emtyResource);
                setDeleteResourceDialog(false);
                setResources([]);
                toast.current?.show({
                    severity: 'success',
                    summary: 'Sucesso!',
                    detail: 'Usuário Deletado com Sucesso!',
                    life: 3000
                });
            }).catch((error) => {
                toast.current?.show({
                    severity: 'error',
                    summary: 'Erro!',
                    detail: 'Erro ao deletar o usuário!',
                    life: 3000
                });
            });
        }
    };
    
    
    const exportCSV = () => {
        dt.current?.exportCSV();
    };
    
    const confirmDeleteSelected = () => {
        setDeleteResourcesDialog(true);
    };
    
    const deleteSelectedResources = () => {
    
        Promise.all(selectedResources.map(async (_resource) => {
            if (_resource.id) {
                await ResourceService.excluir(_resource.id);
            }
        })).then((response) => {
            setResources([]);
            setSelectedResources([]);
            setDeleteResourcesDialog(false);
            toast.current?.show({
                severity: 'success',
                summary: 'Sucesso!',
                detail: 'Usuários Deletados com Sucesso!',
                life: 3000
            });
        }).catch((error) => {
            toast.current?.show({
                severity: 'error',
                summary: 'Erro!',
                detail: 'Erro ao deletar usuários!',
                life: 3000
            })
        });
    };
    
    const onInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, name: string) => {
        const val = (e.target && e.target.value) || '';
        let _resource = { ...resource };
        _resource[`${name}`] = val;
    
        setResource(_resource);
    };
    

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <div className="my-2">
                    <Button label="New" icon="pi pi-plus" severity="success" className="mr-2" onClick={openNew} />
                    <Button label="Delete" icon="pi pi-trash" severity="danger" onClick={confirmDeleteSelected} disabled={!selectedResources || !(selectedResources as any).length} />
                </div>
            </React.Fragment>
        );
    };
    
    const rightToolbarTemplate = () => {
        return (
            <React.Fragment>
                <FileUpload mode="basic" accept="image/*" maxFileSize={1000000} chooseLabel="Import" className="mr-2 inline-block" />
                <Button label="Export" icon="pi pi-upload" severity="help" onClick={exportCSV} />
            </React.Fragment>
        );
    };
    
    const idBodyTemplate = (rowData: Projeto.Resource) => {
        return (
            <>
                <span className="p-column-title">id</span>
                {rowData.id}
            </>
        );
    };
    
    const nameBodyTemplate = (rowData: Projeto.Resource) => {
        return (
            <>
                <span className="p-column-title">Name</span>
                {rowData.name}
            </>
        );
    };
    
    const keyBodyTemplate = (rowData: Projeto.Resource) => {
        return (
            <>
                <span className="p-column-title">key</span>
                {rowData.key}
            </>
        );
    };
    
   
    
    const actionBodyTemplate = (rowData: Projeto.Resource) => {
        return (
            <>
                <Button icon="pi pi-pencil" rounded severity="success" className="mr-2" onClick={() => editResource(rowData)} />
                <Button icon="pi pi-trash" rounded severity="warning" onClick={() => confirmDeleteResource(rowData)} />
            </>
        );
    };
    
    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Users Management</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.currentTarget.value)} placeholder="Search..." />
            </span>
        </div>
    );
    
    const userDialogFooter = (
        <>
            <Button label="Cancel" icon="pi pi-times" text onClick={hideDialog} />
            <Button label="Save" icon="pi pi-check" text onClick={saveResource} />
        </>
    );
    const deleteResourcesDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" text onClick={hideDeleteResourceDialog} />
            <Button label="Yes" icon="pi pi-check" text onClick={deleteResource} />
        </>
    );
    const deleteResourceDialogFooter = (
        <>
            <Button label="No" icon="pi pi-times" text onClick={hideDeleteResourcesDialog} />
            <Button label="Yes" icon="pi pi-check" text onClick={deleteSelectedResources} />
        </>
    );
    
    return (
        <div className="grid crud-demo">
            <div className="col-12">
                <div className="card">
                    <Toast ref={toast} />
                    <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>
    
                    <DataTable
                        ref={dt}
                        value={resources}
                        selection={saveResource}
                        onSelectionChange={(e) => setSelectedResources(e.value as any)}
                        dataKey="id"
                        paginator
                        rows={10}
                        rowsPerPageOptions={[5, 10, 25]}
                        className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} users"
                        globalFilter={globalFilter}
                        emptyMessage="No users found."
                        header={header}
                        responsiveLayout="scroll"
                    >
                        <Column selectionMode="multiple" headerStyle={{ width: '4rem' }}></Column>
                        <Column field="id" header="id" sortable body={idBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        <Column field="name" header="Name" sortable body={nameBodyTemplate} headerStyle={{ minWidth: '15rem' }}></Column>
                        <Column header="login" body={keyBodyTemplate}></Column>
                    
                        <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
                    </DataTable>
    
                    <Dialog visible={resourceDialog} style={{ width: '450px' }} header="Product Details" modal className="p-fluid" footer={userDialogFooter} onHide={hideDialog}>
                        <div className="field">
                            <label htmlFor="name">Name</label>
                            <InputText
                                id="name"
                                value={resource.name}
                                onChange={(e) => onInputChange(e, 'name')}
                                required
                                autoFocus
                                className={classNames({
                                    'p-invalid': submitted && !resource.name
                                })}
                            />
                            {submitted && !resource.name && <small className="p-invalid">Name is required.</small>}
                        </div>
    
                        <div className="field">
                            <label htmlFor="key">key</label>
                            <InputText
                                id="key"
                                value={resource.key}
                                onChange={(e) => onInputChange(e, 'key')}
                                required
                                autoFocus
                                className={classNames({
                                    'p-invalid': submitted && !resource.key
                                })}
                            />
                            {submitted && !resource.key && <small className="p-invalid">key is required.</small>}
                         </div>
                        
                    </Dialog>
    
                    <Dialog visible={deleteResource} style={{ width: '450px' }} header="Confirm" modal footer={deleteResourceDialogFooter} onHide={hideDeleteResourceDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {resource && (
                                <span>
                                    Are you sure you want to delete <b>{resource.name}</b>?
                                </span>
                            )}
                        </div>
                    </Dialog>
    
                    <Dialog visible={deleteResourcesDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteResourcesDialogFooter} onHide={hideDeleteResourcesDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {resources && <span>Are you sure you want to delete the selected users?</span>}
                        </div>
                    </Dialog>
                </div>
            </div>
        </div>
    );
};

export default Resource;


